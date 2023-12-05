package com.example.eatelicious.ui.data.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.eatelicious.R
import com.example.eatelicious.ui.AppScreen
import com.example.eatelicious.ui.theme.spacing


@Composable
fun Drawer(
    currentUserEmail : String,
    onDestinationClicked: (route: String) -> Unit,
) {
    val screens = listOf(
        AppScreen.Home,
        AppScreen.Reservations,
        AppScreen.Logout,
    )

    val personIcons = listOf(
        R.drawable.person_01,
        R.drawable.person_02,
        R.drawable.person_03,
        R.drawable.person_04,
    )

    val spacing = MaterialTheme.spacing

    Column(
        modifier = Modifier
            .width(spacing.drawerWidth)
            .fillMaxHeight()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(
                start = spacing.drawerItemPadding,
                end = spacing.drawerItemPadding,
                top = spacing.xLarge
            )
    ) {

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(personIcons.random())
                        .crossfade(true)
                        .build(),
                    contentScale = ContentScale.Crop,
                    contentDescription = "person",
                    modifier = Modifier
                        .height(100.dp).width(100.dp)
                        .clip(RectangleShape),
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    modifier = Modifier
                        .padding(top = spacing.medium),
                    text = currentUserEmail,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }

        Spacer(modifier = Modifier.width(32.dp))

        screens.forEach { screen ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(spacing.drawerItemHeight)
                    .padding(top = spacing.medium)
                    .clip(RoundedCornerShape(spacing.drawerCornerRadius))
                    .clickable {
                        onDestinationClicked(screen.route)
                    },
                verticalAlignment = CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .padding(
                            start = spacing.drawerIconPadding,
                            end = spacing.drawerIconPadding
                        )
                        .size(spacing.drawerIconSize),
                    painter = painterResource(id = screen.icon),
                    contentDescription = stringResource(id = screen.title),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                )

                Text(
                    text = stringResource(id = screen.title),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}
