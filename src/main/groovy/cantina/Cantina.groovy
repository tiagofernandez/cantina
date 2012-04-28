package cantina

class Cantina {

  static void main(args) {
    def controller = new CantinaController()
    new CantinaView(controller).load()
  }
}
