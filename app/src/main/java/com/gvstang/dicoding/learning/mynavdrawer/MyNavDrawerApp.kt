@file:OptIn(ExperimentalMaterial3Api::class)

package com.gvstang.dicoding.learning.mynavdrawer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gvstang.dicoding.learning.mynavdrawer.ui.theme.MyNavDrawerTheme

@Composable
fun MyNavDrawerApp() {

    val appState = rememberMyNavDrawerState()

    MyNavigationDrawer(
        drawerState = appState.drawerState,
        drawerContent = {
            MyNavigationContent(
                onItemSelected = { appState.onItemSelected(it) }
            )
        }
    ) {
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = appState.snackbarHostState)
            },
            topBar = {
                MyTopBar {
                    appState.onMenuClick()
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(text = stringResource(id = R.string.hello_world))
            }

        }
    }
}

@Composable
fun MyNavigationDrawer(drawerContent: @Composable () -> Unit, drawerState: DrawerState, content: @Composable () -> Unit) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = drawerContent,
        gesturesEnabled = true
    ) {
        content()
    }
}

data class MenuItem(val title: String, val icon: ImageVector)

@Composable
fun MyNavigationContent(
    onItemSelected: (String) -> Unit
) {
    val items = listOf(
        MenuItem(
            title = stringResource(R.string.home),
            icon = Icons.Default.Home
        ),
        MenuItem(
            title = stringResource(R.string.favourite),
            icon = Icons.Default.Favorite
        ),
        MenuItem(
            title = stringResource(R.string.profile),
            icon = Icons.Default.AccountCircle
        ),
    )
    val selected = remember { mutableStateOf(items[0]) }

    ModalDrawerSheet {
        items.forEach { item ->
            NavigationDrawerItem(
                modifier =
                when(item) {
                    items.first(), items.last() -> Modifier.padding(8.dp)
                    else -> Modifier.padding(horizontal = 8.dp)
                },
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                label = { Text(text = item.title) },
                selected = item == selected.value,
                onClick = {
                    selected.value = item
                    onItemSelected(item.title)
                }
            )
        }
    }
}

@Composable
fun MyTopBar(onMenuClick: () -> Unit) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = {
                onMenuClick()
            }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = stringResource(id = R.string.menu)
                )
            }
        },
        title = {
            Text(text = stringResource(id = R.string.app_name))
        },
    )
}

@Preview
@Composable
fun MyNavDrawerAppPreview() {
    MyNavDrawerTheme {
        MyNavDrawerApp()
    }
}