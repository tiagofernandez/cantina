package cantina

import static cantina.CantinaUtils.*

class CantinaController {

  final basePath = "\\\\NCE-TSTSEP-D2\\tfernandez\\cantina\\csv"

  List getGoingList() {
    participantsDatabase.goingList()
  }

  void setGoing() {
    participantsDatabase.setGoing(username)
  }

  void setNotGoing() {
    participantsDatabase.setNotGoing(username)
  }

  List getAllComments() {
    commentsDatabase.allComments().collect { "${it[2]} ${it[0]}: ${truncate(it[1], 50)}" }
  }

  void addComment(comment) {
    commentsDatabase.addComment(username, comment)
  }

  private String getUsername() {
    // InetAddress.localHost.hostName
    System.getProperty('user.name')
  }

  private ParticipantsDatabase getParticipantsDatabase() {
    def filePath = "${basePath}\\${this.currentDate}.csv"
    new ParticipantsDatabase(filePath)
  }

  private CommentsDatabase getCommentsDatabase() {
    def filePath = "${basePath}\\${this.currentDate}_comments.csv"
    new CommentsDatabase(filePath)
  }

  private String getCurrentDate() {
    currentDate('yyyyMMdd')
  }
}
