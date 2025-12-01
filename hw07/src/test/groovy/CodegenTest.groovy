import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import java.io.File

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir

class CodegenTest {

    @TempDir
    File projectDir

    private File getSettingsFile() {
        return new File(projectDir, "settings.gradle")
    }

    private File getBuildFile() {
        return new File(projectDir, "build.gradle")
    }

    @Test
    void testCodegenTask() {
        settingsFile.write("") // single-project build
        buildFile.write("""
        plugins {
            id 'generateCode'
        }
        generateCode {
            fieldName = 'fieldExample'
            className = 'ClassExample'
            packageName = 'org.otus.example'
            output = ''
        }
        """.stripIndent())

        def result = GradleRunner.create()
                .withProjectDir(projectDir)
                .withPluginClasspath()
                .withArguments("generateCode")
                .forwardOutput()
                .build()

        def fileExample = new File ("\\org\\otus\\example\\ClassExample")
        assertTrue(fileExample.exists())
        def fileText = fileExample.text
        def expectedText =
        """ package org.otus.example;
            public class ClassExample {
                private String fieldExample
            }
        """.stripIndent()
        assertEquals(expectedText, fileText)

        // Assert: verify console output and successful task outcome
        //assertTrue(result.task(":greet")?.outcome == TaskOutcome.SUCCESS)
    }
}
