
plugins {
    id("java-library")
}
apply(from = "$rootDir/gradle/groups/mainProjects.gradle")

repositories {
    jcenter()
    maven(url = "https://kotlin.bintray.com/kotlinx")
}

dependencies {

    // Dynamic Component Registration
    api(Libs.javax_inject)
    api(Libs.guice)
    api(Libs.classgraph)

    // core utilities
    api(Libs.com_eden_common)
    api(Libs.clog4j)
    api(Libs.krow)
    api(Libs.okhttp)
    api(Libs.commons_io)
    api(Libs.commons_lang3)
    api(Libs.thumbnailator)
    
    // validation
    api(Libs.validation_api)
    implementation(Libs.hibernate_validator)
    implementation(Libs.javax_el)

    // Included parsers: JSON, YAML, TOML, CSV, Pebble, Markdown, Sass
    api(Libs.json)
    implementation(Libs.snakeyaml)
    implementation(Libs.toml4j)
    implementation(Libs.pebble)
    implementation(Libs.jsass)
    implementation(Libs.univocity_parsers)

    // Flexmark extensions
    api(Libs.flexmark)
    implementation(Libs.flexmark_ext_aside)
    implementation(Libs.flexmark_ext_attributes)
    implementation(Libs.flexmark_ext_enumerated_reference)
    implementation(Libs.flexmark_ext_gfm_tasklist)
    implementation(Libs.flexmark_ext_toc)
    implementation(Libs.flexmark_ext_anchorlink)

    // server
    implementation(Libs.nanohttpd)
    implementation(Libs.nanohttpd_websocket)
    
    testImplementation(Modules.OrchidTest)
}
