apply {
    from("$rootDir/compose-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.coreUi))

    "implementation"(project(Modules.imageFinderDomain))

    "implementation"(Coil.coilCompose)

    "implementation"(Accompanist.accompanistPlaceHolder)

    "implementation"(Paging.paging)
    "implementation"(Paging.composePaging)

}