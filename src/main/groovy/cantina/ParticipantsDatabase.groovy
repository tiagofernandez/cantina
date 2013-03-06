package cantina

class ParticipantsDatabase extends CantinaDatabase {

  ParticipantsDatabase(filePath) {
    super(filePath, ['NAME', 'GOING'])
  }

  List goingList() {
    refreshRows()
    rows.findAll { it[1] == 'yes' }.collect { it[0] }
  }

  void setGoing(name) {
    setGoingFlag(name, 'yes')
  }

  void setNotGoing(name) {
    setGoingFlag(name, 'no')
  }

  private void setGoingFlag(name, value) {
    refreshRows()
    def row = findRow(name)
    row[0] = name
    row[1] = value
    persist()
  }

  private def findRow(name) {
    def row = rows.find { it[0] == name }
    if (!row) {
      row = [name, '?']
      rows << row
    }
    return row
  }
}
