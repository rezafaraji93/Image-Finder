apply {
    from("$rootDir/compose-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.coreUi))

    "implementation"(project(Modules.imageFinderDomain))

    "implementation"(Coil.coilCompose)

    "implementation"(Paging.paging)
    "implementation"(Paging.composePaging)

}