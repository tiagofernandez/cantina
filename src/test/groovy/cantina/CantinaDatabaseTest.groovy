package cantina

import cantina.CantinaDatabase

class CantinaDatabaseTest extends GroovyTestCase {

  final filePath = 'target/cantina.csv'
  final database = new CantinaDatabase(filePath)

  CantinaDatabaseTest() {
    new File(filePath).delete()
  }

  void test_should_get_empty_going_list_if_no_csv_exists() {
    assert database.goingList().empty
  }

  void test_should_add_person_to_going_list() {
    database.setGoing('Tiago')
    assert database.goingList().size() == 1
  }

  void test_should_remove_person_from_going_list() {
    database.setNotGoing('Tiago')
    assert database.goingList().empty
  }

  void test_should_re_add_person_to_going_list() {
    database.setGoing('Tiago')
    assert database.goingList().size() == 1
  }
}
