package cantina

import cantina.CantinaController

class CantinaControllerTest extends GroovyTestCase {

  def controller = new CantinaController()

  void test_should_get_username_from_local() {
    assert controller.username != null
  }

  void test_should_get_current_date_string() {
    assert controller.currentDate != null
  }
}
