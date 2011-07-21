GrailsMDAScripts
===

Although [Grails] includes scripts for the creation of applications and domain classes, it does not include scripts to allow the developer to define domain attributes, constraints or realtionships with other domain classes. GrailsMDAScripts is a set of scripts and updated templates that can be used to define domain attributes from the Grails command line interface.
[Grails]: http://grails.org/

Prerequisites
---

You need a Grails 1.3.7. To install Grails, visit http://grails.org/Download and download the version you would like to use. Set a GRAILS_HOME environment variable to point to the root of the extracted download and add GRAILS_HOME/bin to your executable PATH. Then in a shell, type the following:
	
        
Install
---
Install the provided scripts.

    Linux / Mac
    cp scripts/CreateDomainAttribute.groovy $GRAILS_HOME/scripts/.
    
        
Install the updated domain class template.

    Linux / Mac
    cp templates/DomainClass.groovy $GRAILS_HOME/src/grails/templates/artifacts/
    
Usage
---

CreateDomainAttribute.groovy
Actions:
    Save a backup of the current domain class
    Add a specified attribute and constraint to the specified domain class
    
    $ grails create-domain-attribute <domain-class-package-and-name> <attribute-name> <data-type> [constraint]*

Example 1:
    Add "username" attribute of type String in $basedir/grails-app/domain/org/something.Object

    $ grails create-domain-attribute org.something.Object username String
    
Example 2:
    Add "username" attribute of type String in $basedir/grails-app/domain/org/something.Object
    Adds constraint definition to constraints block:
        username blank:true, size:5..24, unique:true

    $grails create-domain-attribute org.something.Object username String blank:false size:3..24 unique:true
	
License
---
Copyright 2011 William E. Stephens (https://github.com/wistephens)

This project is free software released under the MIT license:
http://www.opensource.org/licenses/mit-license.php 