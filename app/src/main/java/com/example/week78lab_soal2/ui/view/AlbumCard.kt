package com.example.week78lab_soal2.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.week78lab_soal2..data.dto.Album

// Gruvbox Colors
private val GruvboxBg = Color(0xFF282828)
private val GruvboxBg2 = Color(0xFF504945)
private val GruvboxFg = Color(0xFFEBDBB2)
private val GruvboxOrange = Color(0xFFD65D0E)

@Composable
fun AlbumCard(
    album: Album,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = GruvboxBg2
        ),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(2.dp, GruvboxBg)
    ) {
        Column {
            // Album Cover
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .background(GruvboxBg)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(album.strAlbumThumb)
                        .crossfade(true)
                        .build(),
                    contentDescription = album.strAlbum,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            // Album Info
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = album.strAlbum,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = GruvboxFg,
                    maxLines = 2,
                    minLines = 2
                )

                Spacer(modifier = Modifier.height(4.dp))

                album.intYearReleased?.let {
                    Text(
                        text = it,
                        fontSize = 12.sp,
                        color = GruvboxOrange
                    )
                }
            }
        }
    }
}