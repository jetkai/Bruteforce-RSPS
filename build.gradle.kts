plugins {
    kotlin("jvm") version "1.5.10"
    application
}

dependencies {
    implementation("org.benf:cfr:0.151")
    implementation("commons-codec:commons-codec:1.15")
    implementation("commons-logging:commons-logging:1.2")
    implementation("info.picocli:picocli:4.6.1")
    implementation("org.json:json:20210307")
}

repositories {
    mavenCentral()
    jcenter()
}

group = "aniski.Main"
version = "1.0"

tasks.withType<Jar> {
    manifest {
        attributes["Implementation-Title"] = project.name
        attributes["Implementation-Version"] = project.version
        attributes["Main-Class"] = "aniski.Main"
    }
    from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)
    from({ configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) } })
    application { mainClassName = "aniski.Main"}
}

tasks {
    "build" {
        dependsOn()
    }
}

subprojects {
    apply(plugin = "java-library")

    group = rootProject.group
    version = rootProject.version

    repositories {
        mavenCentral()
        jcenter()
    }
}

application {
    mainClassName = "Main"
}

