package cantina

class CommentsDatabaseTest extends GroovyTestCase {

  final filePath = 'target/cantina_comments.csv'
  final database = new CommentsDatabase(filePath)

  CommentsDatabaseTest() {
    new File(filePath).delete()
  }

  void test_should_get_empty_comments_if_no_csv_exists() {
    assert database.allComments().empty
  }

  void test_should_add_comment() {
    database.addComment('Tiago', '12:15 hj')
    assert database.allComments().size() == 1
  }

  void test_should_add_yet_another_comment() {
    database.addComment('Tiago', 'ok?')
    assert database.allComments().size() == 2
  }
}
