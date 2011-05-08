package easel
import java.net.URL
import sbt._

trait DocBookPlugin extends DefaultProject {

  lazy val hello = task { log.info("Hello World!"); None }
  val docBookVersion = "1.76.1"
  val docBookXslUrl = new URL("http://sourceforge.net/projects/docbook/files/docbook-xsl/" + 
    docBookVersion + "/docbook-xsl-" + docBookVersion + ".zip/download")
  val docBookXslRoot:Path = managedDependencyPath
  val docBookXslPath:Path = docBookXslRoot / ("docbook-xsl-" + docBookVersion)


  lazy val compileDocBook = dynamic(compileDocBookAction) describedAs "Compiles DocBook"
  def compileDocBookAction = task{ compileDocBookTask }.named("compile-docbook")
  override def compileAction = super.compileAction.dependsOn(compileDocBook)
  def compileDocBookTask: Option[String] = {
		log.info("Compiling docbook")
		None
  }
  
  lazy val updateDocBook = dynamic(updateDocBookAction) describedAs "Updates DocBook"
  def updateDocBookAction = task{ updateDocBookTask }.named("update-docbook").dependsOn(super.updateAction)
  override def updateAction = updateDocBookAction

  def updateDocBookTask: Option[String] = { 
    log.info("Updating docbook stylesheets in " + docBookXslRoot)

		if (!docBookXslRoot.isDirectory) {
	      log.info("Creating " + docBookXslRoot)
			  FileUtilities.createDirectory(docBookXslRoot, log)
	  }
		if (!docBookXslPath.isDirectory) {
	  	log.info("Acquiring docbook xsl from " + docBookXslUrl + ", extracting to " + docBookXslPath)
		  FileUtilities.unzip(docBookXslUrl, docBookXslRoot, log).left.foreach(error)
		}	
		None
  } 


}

