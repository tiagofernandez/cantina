package cantina

import java.text.SimpleDateFormat

class CantinaController {

  final basePath = "\\\\NCE-TSTSEP-D2\\tfernandez\\cantina\\csv"

  List getGoingList() {
    database.goingList()
  }

  void setGoing() {
    database.setGoing(username)
  }

  void setNotGoing() {
    database.setNotGoing(username)
  }

  String getUsername() {
    // InetAddress.localHost.hostName
    System.getProperty('user.name')
  }

  private CantinaDatabase getDatabase() {
    def filePath = "${basePath}\\${this.currentDate}.csv"
    new CantinaDatabase(filePath)
  }

  private String getCurrentDate() {
    def formatter = new SimpleDateFormat('yyyyMMdd')
    formatter.format(new Date())
  }
}
