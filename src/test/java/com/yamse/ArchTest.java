package com.yamse;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.yamse");

        noClasses()
            .that()
            .resideInAnyPackage("com.yamse.service..")
            .or()
            .resideInAnyPackage("com.yamse.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.yamse.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
