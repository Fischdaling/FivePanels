plugins {
    id("java")
}

group = "org.theShire"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.nulab-inc:zxcvbn:1.9.0")
    implementation("at.favre.lib:bcrypt:0.10.2")
    implementation("org.antlr:stringtemplate:4.0.2")
}
java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21

}