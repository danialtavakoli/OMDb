package com.danialtavakoli.omdb.core

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


/**
 * CodeChallengeApplication is the application class for the OMDB app.
 *
 * This class extends [Application] and is annotated with [HiltAndroidApp],
 * which triggers Hilt's code generation including a base class for the application
 * that serves as the application-level dependency container.
 *
 * By using the [HiltAndroidApp] annotation, Hilt will generate Dagger components
 * for the application, making it possible to perform dependency injection
 * throughout the application.
 *
 * The [CodeChallengeApplication] class itself doesn't override any methods from [Application],
 * but it serves as the entry point for Hilt's dependency injection.
 */
@HiltAndroidApp
class CodeChallengeApplication : Application()