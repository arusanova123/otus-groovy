import org.gradle.api.Plugin
import org.gradle.api.Project

class CodegenPlugin implements Plugin <Project> {
    @Override
    void apply (Project target) {
        Project prj = target
        def extension = prj.extensions.create('codegen', CodegenExtension)
        prj.tasks.register('generateCode', CodegenTask) {
            it.fieldName = extension.fieldName
            it.className = extension.className
            it.packageName = extension.packageName
            it.output = extension.output
        }
/*
        prj.afterEvaluate {
            target.tasks.named('compileGroovy') {
                it.dependsOn (prj.tasks.named ('generateCode'))
            }
        }
*/
        //prj.getProperties().get("sourceSets").getByName("main").getResources().srcDir(extension.output)
        //sourceSets.main.java.srcDir extension.output
    }
}
