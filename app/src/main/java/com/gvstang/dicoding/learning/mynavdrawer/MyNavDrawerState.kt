@file:OptIn(ExperimentalMaterial3Api::class)

package com.gvstang.dicoding.learning.mynavdrawer

import android.content.Context
import android.widget.Toast
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MyNavDrawerState(
    val drawerState: DrawerState,
    val snackbarHostState: SnackbarHostState,
    val scope: CoroutineScope,
    val context: Context
) {
    fun onMenuClick() {
        scope.launch {
            drawerState.open()
        }
    }

    fun onItemSelected(title: String) {
        scope.launch {
            drawerState.close()
            val snackbarResult = snackbarHostState.showSnackbar(
                message = context.resources.getString(R.string.coming_soon, title),
                actionLabel = context.resources.getString(R.string.subscribe_question)
            )
            if(SnackbarResult.ActionPerformed == snackbarResult) {
                Toast.makeText(
                    context,
                    context.resources.getString(R.string.subscribed_info),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

@Composable
fun rememberMyNavDrawerState(
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    scope: CoroutineScope = rememberCoroutineScope(),
    context: Context = LocalContext.current,
): MyNavDrawerState = remember(drawerState, snackbarHostState, scope, context) {
    MyNavDrawerState(drawerState, snackbarHostState, scope, context)
}