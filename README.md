# Kotlin Localization

In this project, I tried to explain how do localization in Kotlin.

# Kotlin Localization

In this project, I tried to explain how do localization in Kotlin.

# Dependencies

```

dependencies {
  implementation("androidx.appcompat:appcompat:1.6.1")
}
```

# Add this code to build.gradle (app-level)

```
android {
  bundle {
    language {
      enableSplit = false
    }
  }
}
```

# Change Localization code

```
fun setLocale(locale: String) {
    AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.forLanguageTags(locale)
    )
    Locale.setDefault(Locale(locale))
}

setLocale("de") // Change localization
```

## Fetch current localization

```
val currentLocale = Locale.getDefault().toLanguageTag()
```

# Turkish Localization

![localization_tr.jpeg](assets%2Flocalization_tr.jpeg)

# English Localization

![localization_en.jpeg](assets%2Flocalization_en.jpeg)

# German Localization

![localization_de.jpeg](assets%2Flocalization_de.jpeg)