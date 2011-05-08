import sbt._
import java.net.URL

class DocBookPluginProject(info: ProjectInfo) extends PluginProject(info) {
	val xalan = "xalan" % "xalan" % "2.7.1"
	val fop = "org.apache.xmlgraphics" % "fop" % "1.0"

  val docBookVersion = "1.76.1"
  val docBookXslUrl = new URL("http://sourceforge.net/projects/docbook/files/docbook-xsl/" + 
    docBookVersion + "/docbook-xsl-" + docBookVersion + ".zip/download")
  val docBookXslRoot:Path = managedDependencyPath / ("docbook-xsl-" + docBookVersion)


  lazy val updateDocBookAction = task { 
		log.info("Acquiring docbook xsl from " + docBookXslUrl)
		if (!docBookXslRoot.isDirectory) {
		  FileUtilities.unzip(docBookXslUrl, outputRootPath, log).left.foreach(error)
		}	
		None
  }
	override def updateAction = super.updateAction.dependsOn(updateDocBookAction)


	lazy val compileDocBookAction = task { 
	  log.info("Compiling DocBook")
		None
  }
	override def compileAction = super.compileAction.dependsOn(compileDocBookAction)
}
