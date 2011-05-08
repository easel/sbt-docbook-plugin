package easel
import java.net.URL
import sbt._

trait DocBookPlugin extends DefaultProject {

  lazy val hello = task { log.info("Hello World!"); None }
  val docBookVersion = "1.76.1"
  val docBookXslUrl = new URL("http://sourceforge.net/projects/docbook/files/docbook-xsl/" + 
    docBookVersion + "/docbook-xsl-" + docBookVersion + ".zip/download")
  val docBookXslRoot:Path = managedDependencyPath / ("docbook-xsl-" + docBookVersion)


  lazy val compileDocBook = dynamic(compileDocBookAction) describedAs "Compiles DocBook"
  def compileDocBookAction = task{ compileDocBookTask }.named("compile-docbook")
  override def compileAction = super.compileAction.dependsOn(compileDocBook)
  def compileDocBookTask: Option[String] = {
		log.info("Compiling docbook")
		None
  }
  
  lazy val updateDocBook = dynamic(updateDocBookAction) describedAs "Updates DocBook"
  def updateDocBookAction = task{ updateDocBookTask }.named("update-docbook")
  override def updateAction = super.updateAction.dependsOn(updateDocBookAction)

  def updateDocBookTask: Option[String] = { 
		log.info("Acquiring docbook xsl from " + docBookXslUrl)
		if (!docBookXslRoot.isDirectory) {
		  FileUtilities.unzip(docBookXslUrl, outputRootPath, log).left.foreach(error)
		}	
		None
  } 


}

