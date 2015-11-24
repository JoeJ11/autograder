val answer = Array("absence","admitted","adults","agent","agreements","alexander","alien","alink","allan","angles","appressian","approval","arbitrary","archives","arrest","assumes","attacking","aux","avenue","avoided","ban","banned","bars","bases","bearing","begun","beleive","biased","brent","brett","bright","bullet","bulletin","buried","cad","cae","calpoly","canadareferences","capacity","careferences","ccs","checks","chuck","circle","cit","cite","cited","cities","clinical","closely","clothes","cmuvm","collect","collins","colour","comfollowup","comm","communities","concise","confirmed","confusion","connections","cons","conscience","constantly","consulting","contrast","contrib","converted","converting","cooperation","copied","corner","cultural","cunixb","cupertino","cupnews","cursor","custom","cx","da","darkstar","dcs","decade","declare","definitions","delta","destroy","detectors","developer","devoted","dictionary","discover","distinction","dozen","drag","drawn","dsinc","dunn","edufrom","eduto","eecs","elected","elvis","employment","engineers","ethernet","ethical","examination","examined","exclusive","existed","existent","expand","expo","expression","february","felix","fl","flexible","floppies","formatted","founded","frames","francis","freely","freeman","gatewayfrom","generator","giants","glory","glue","gn","gregg","guard","guidelines","hair","header","headwall","heavily","hitting","houses","howard","hz","icons","ideal","implied","incidents","increases","incredibly","indians","infrastructure","ingr","institutions","int","interpreted","io","iranian","iron","isthe","isu","joint","jp","julian","jumpers","kennedy","kick","kills","kings","laboratorylines","lake","largest","laurentian","lawyer","layer","legally","lemieux","lh","limbaughsubject","lobby","locations","loose","madison","malines","maple","mapping","marked","mcrcim","mcs","megs","melkonian","mere","messiah","mik","miscorganization","modules","monu","morgan","motion","motives","mtholyoke","naive","nelson","newshub","newsme","nh","noose","november","offset","ohanus","ole","oneof","oops","oregon","originssubject","outputs","panasonic","password","patch","payload","pays","pen","penalty","pennsylvania","performed","permits","personality","phenomenon","philadelphia","phillies","polygon","printers","procedures","propose","protocols","psychological","publicly","punishment","pure","qgouk","quoting","ramsey","rare","rat","rawlins","readme","realistic","recorded","removing","rep","replied","repost","requests","resemblance","responded","restrict","restrictions","rev","reveal","revealed","rider","rise","rln","rod","roms","ronald","roy","rphroy","rtsg","sahak","sake","sas","satisfied","sb","scratch","searched","searching","sector","selective","sensitivity","sentence","sera","serafrom","serbs","serves","shadow","shaped","shit","siemens","silicon","sins","slip","smooth","specs","spreading","ssd","strength","strict","stupidity","suffer","sunos","supplies","surrounding","suspension","swear","sweden","syndrome","teachings","teeth","telecom","temperature","terrorism","terrorists","terry","tickets","tiff","topics","tourist","trap","ucla","uky","ultb","union","unusual","upper","usage","vacation","vacuum","valley","valuable","vast","vehicles","violence","wars","wednesday","widget","wright","writers","writings","xlib","yellow","za","zazen","zuma")

import java.io._
import scala.io.Source
import scala.util.parsing.json.JSON

val path = args(0).toString

def writeToFile(score:Int, comment:String) = {
  val writer = new PrintWriter(new File(path + "/response"))
  writer.write(score + "\n")
  writer.write(1 + "\n")
  writer.write("<p>" + comment + "</p>")
  writer.close()
}

val stu = Source.fromFile(path + "/answer").getLines.map(line => {
  val b = JSON.parseFull(line)
  val result = b match {  
    case Some(map: Map[String, List[Map[String, List[String]]]]) => {
      try {
        val keys = map.asInstanceOf[Map[String, List[Map[String, List[String]]]]].keys.toArray
        keys(0)
      } catch {
        case e: ClassCastException => {writeToFile(0, "Sorry, it seems that metadata is not currect."); System.exit(-1); ""}
      }
    }
    case None => {writeToFile(0, "Parsing failed"); System.exit(-1);""}
    case other => {writeToFile(0, "Unknown data structure: " + other); System.exit(-1);""}
  }
  if(result.isInstanceOf[String]) result
  else ""
}).toArray.sortBy(x => x)
val dif = answer.diff(stu)
if(dif.size == 0) writeToFile(1, "Cool!")
else writeToFile(0, "0ops, some keys is not currect.")
