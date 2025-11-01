package com.example.week78lab_soal2.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.week78lab_soal2.data.dto.Album
import com.example.week78lab_soal2.data.dto.Artist
import com.example.week78lab_soal2.ui.view.AlbumCard
import com.example.week78lab_soal2.ui.view.ErrorView
import com.example.week78lab_soal2.ui.view.LoadingView
import com.example.week78lab_soal2.ui.model.ArtistUiState
import com.example.week78lab_soal2.ui.viewmodel.ArtistViewModel

// Gruvbox Colors
private val GruvboxBg = Color(0xFF282828)
private val GruvboxBg1 = Color(0xFF3C3836)
private val GruvboxBg2 = Color(0xFF504945)
private val GruvboxFg = Color(0xFFEBDBB2)
private val GruvboxYellow = Color(0xFFD79921)
private val GruvboxOrange = Color(0xFFD65D0E)

@Composable
fun ArtistView(
    modifier: Modifier = Modifier,
    viewModel: ArtistViewModel = viewModel(),
    onAlbumClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(GruvboxBg1)
    ) {
        when (val state = uiState) {
            is ArtistUiState.Loading -> {
                LoadingView(message = "Loading artist data...")
            }
            is ArtistUiState.Success -> {
                ArtistSuccessView(
                    artist = state.artist,
                    albums = state.albums,
                    onAlbumClick = onAlbumClick
                )
            }
            is ArtistUiState.Error -> {
                ErrorView(
                    message = state.message,
                    onRetry = { viewModel.retry() }
                )
            }
        }
    }
}

@Composable
fun ArtistSuccessView(
    artist: Artist,
    albums: List<Album>,
    onAlbumClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Artist Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(GruvboxBg2)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(artist.strArtistBanner ?: artist.strArtistThumb)
                    .crossfade(true)
                    .build(),
                contentDescription = "Artist Banner",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        // Artist Info
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Artist Name
            Text(
                text = artist.strArtist,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = GruvboxYellow
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Genre
            artist.strGenre?.let {
                Text(
                    text = it,
                    fontSize = 16.sp,
                    color = GruvboxOrange,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Biography
            artist.strBiographyEN?.let { bio ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = GruvboxBg2
                    ),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(2.dp, GruvboxBg)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Biography",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = GruvboxYellow
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = bio.take(300) + if (bio.length > 300) "..." else "",
                            fontSize = 14.sp,
                            color = GruvboxFg,
                            lineHeight = 20.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Albums Title
            Text(
                text = "Albums",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = GruvboxYellow
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        // Albums Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.height(600.dp) // Fixed height for grid
        ) {
            items(albums) { album ->
                AlbumCard(
                    album = album,
                    onClick = { onAlbumClick(album.idAlbum) }
                )
            }
        }
    }
}