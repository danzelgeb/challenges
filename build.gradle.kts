plugins {
    id("java")
    id("xyz.jpenilla.run-paper") version "2.3.1"
    id ("com.gradleup.shadow") version "9.0.0-beta4"
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.12"
}

group = "dev.danzel"
version = project.version

repositories {
    mavenCentral()
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven {
        name = "aikar"
        url = uri("https://repo.aikar.co/content/groups/aikar/")
    }
}

dependencies {
    paperweight.paperDevBundle("1.21.4-R0.1-SNAPSHOT")
    implementation("org.projectlombok:lombok:1.18.30")
    implementation("co.aikar:acf-paper:0.5.1-SNAPSHOT")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    annotationProcessor("co.aikar:acf-paper:0.5.1-SNAPSHOT")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks.assemble {
    dependsOn(tasks.reobfJar)
}

tasks {
    runServer {
        minecraftVersion("1.21.4")
        downloadPlugins {
            modrinth("Vebnzrzj", "v5.5.53-bukkit") // LuckPerms
            modrinth("P1OZGk5p", "5.10.1-SNAPSHOT+1012") // ViaVersion
        }
    }
}