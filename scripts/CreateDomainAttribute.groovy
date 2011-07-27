/*
 * Copyright 2004-2005 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Gant script that creates a Grails domain class
 *
 * @author Graeme Rocher
 *
 * @since 0.4
 */

includeTargets << grailsScript("_GrailsInit")
//includeTargets << grailsScript("_GrailsCreateArtifacts")

USAGE = """
Usage: grails create-domain-attribute <domain-class-package-and-name> <attribute-name> <data-type> [constraint]*

Adds a specified attribute and constraint to the specified domain class

Example: grails create-domain-attributes org.something.Object username String
Adds "String username" as an attribute in $basedir/grails-app/domain/org/something.Object
    
Example: grails create-domain-attributes org.something.Object username String blank:false size:3..24 unique:true
Adds "String username" as an attribute in $basedir/grails-app/domain/org/something.Object
Adds constraint definition to constraints block:
    username blank:true, size:5..24, unique:true
"""

appDir = "$basedir/grails-app"
domainDir = "$appDir/domain"
className = ''
attributeName = ''
typeName = ''
constraints = ''

attributeLocation = "//ATTRIBUTES"
constraintLocation = "//CONSTRAINTS"

target('create-domain-attribute': "Add attribute to existing domain class") {
	depends(checkVersion, configureProxy, classpath)

        parseArgs()
	updateDomain()

	ant.echo """
*******************************************************
* Added specified attrubute to your Grails domain     *
*******************************************************
"""
}




private void updateDomain() {

    attributeText = attributeLocation + "\n\t" + typeName + " " + attributeName
    
    ant.echo message: attributeText
    
    constraintText = constraintLocation + "\n\t" + attributeName + " (" + constraints + ")"
    
    ant.echo message: constraintText


    def domainFile = new File(domainDir, className)
    if (domainFile.exists()) {
        
        fileText = domainFile.text;
        
        // save backup file
        backupFile = new File(domainFile.path + ".bak");
        backupFile.write(fileText);
        
        // Insert attrubute text
        fileText = fileText.replaceAll(attributeLocation, attributeText)
        fileText = fileText.replaceAll(constraintLocation, constraintText)
        
        // write file
        domainFile.write(fileText);
    }
    else {
        ant.echo message: "Class '${className}' Not found in '${domainDir}'"
    }
}

private parseArgs() {
	args = args ? args.split('\n') : []
        
        ant.echo message: "ARGS size = " + args.size() 
        
	if (args.size() == 3) {
            (classNm, attributeName, typeName) = args
            ant.echo message: "ClassNm = " + classNm
            className = classNm.replaceAll(~/\./, "/")
            
            ant.echo message: "Updating class '${className}' with attribute '${attributeName}' of type '${typeName}'"
        }
        else if (args.size() >= 4) {
                
            if (args.size() == 4) {
                (classNm, attributeName, typeName, constraints) = args
                className = classNm.replaceAll(~/\./, "/")
            }
            else {
                (classNm, attributeName, typeName) = args[0..2]
                className = classNm.replaceAll(~/\./, "/")
                def cons = args[3..args.size()-1]
                constraints = cons.join(',')
            }
                
            ant.echo message: "Updating class '${className}' with attribute '${attributeName}' of type '${typeName}' having constraint of ${constraints}"
        }
        else {
                ant.echo message: USAGE
                System.exit(1)
	}
        
        if (! className.endsWith(".groovy")) {
            className += ".groovy"
        }
}

setDefaultTarget 'create-domain-attribute'
