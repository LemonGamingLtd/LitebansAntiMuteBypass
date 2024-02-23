plugins {
    java
    `java-library`

    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.3"
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")

    tasks {
        compileJava  {
            sourceCompatibility = "17"
            targetCompatibility = "17"
        }
    }

    dependencies {
        compileOnly("io.papermc.paper:paper-api:1.20.2-R0.1-SNAPSHOT")
        compileOnly("com.gitlab.ruany:LiteBansAPI:0.5.0")
        implementation("com.github.NahuLD:folia-scheduler-wrapper:v0.0.2")
    }

    repositories {
        mavenCentral()

        maven {
            url = uri("https://papermc.io/repo/repository/maven-public/")
        }

        maven {
            url = uri("https://oss.sonatype.org/content/groups/public/")
        }

        maven {
            url = uri("https://jitpack.io")
        }
    }
}

tasks {
    shadowJar {
        archiveClassifier.set("")

        relocate("me.nahu.scheduler", "me.kyrobi.scheduler")
    }

    assemble {
        dependsOn("shadowJar")
    }
}

bukkit {
    main = providers.gradleProperty("mainClass").get()
    apiVersion = "1.16"
    depend = listOf("LiteBans")

}