import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

class CodegenTask extends DefaultTask {
    @Input
    String fieldName
    @Input
    String packageName
    @Input
    String className
    @Input
    String output

    @TaskAction
    void generate () {
        def packagePath = packageName.replace (".","/")
        def packageDir = new File (output, packagePath)
        if (!packageDir.exists())
            packageDir.mkdirs()
        def javaFile = new File (packageDir, "$className")
        javaFile.text = generateClassContent()
    }

    String generateClassContent() {
        """ package $packageName;
            public class $className {
                private String $fieldName
            }
        """.stripIndent()
    }
}
