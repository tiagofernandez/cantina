package com.github.tiagofernandez

import org.h2.tools.Csv
import org.h2.tools.SimpleResultSet

import java.sql.Types
import java.sql.ResultSet

class CantinaDatabase {

  private final def columns = ['NAME', 'GOING']
  private final def rows = []

  private final String filePath

  CantinaDatabase(String filePath) {
    this.filePath = filePath
  }

  List goingList() {
    refreshRows()
    def result = []
    rows.each { row ->
      if (row[1] == 'yes')
        result << row[0]
    }
    result
  }

  void setGoing(String name) {
    setGoingFlag(name, 'yes')
  }

  void setNotGoing(String name) {
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

  private void persist() {
    def rs = new SimpleResultSet()
    columns.each { column ->
      rs.addColumn(column, Types.VARCHAR, 255, 0)
    }
    rows.each { row ->
      rs.addRow(row as String[])
    }
    new Csv().write(filePath, rs, null)
  }

  private void refreshRows() {
    rows.clear()
    if (new File(filePath).exists()) {
      ResultSet rs = new Csv().read(filePath, null, null)
      while (rs.next()) {
        rows << columns.inject([], { row, column ->
          row << rs.getString(column)
        })
      }
      rs.close()
    }
  }
}
