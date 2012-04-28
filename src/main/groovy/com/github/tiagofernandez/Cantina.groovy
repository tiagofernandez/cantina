package com.github.tiagofernandez

class Cantina {

  static void main(args) {
    def controller = new CantinaController()
    new CantinaView(controller).load()
  }
}
