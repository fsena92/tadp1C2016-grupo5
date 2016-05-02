module Matcheadores
  def val(un_valor)
    Matcher_val.new(un_valor)
  end

  def type(un_tipo)
    Matcher_type.new(un_tipo)
  end

  def duck(*metodos)
    Matcher_duck_typing.new(*metodos)
  end

  def list(una_lista, *condicion)
    Matcher_list.new(una_lista, *condicion)
  end
end

class Object
  include Matcheadores

  def matches?(un_objeto, &bloque)
    pattern = Pattern.new(un_objeto)
    pattern.instance_eval &bloque
    pattern.matchear
  end
end

module Matcher
  def and(*matchers)
    Matcher_and_combinator.new(self, *matchers)
  end

  def or(*matchers)
    Matcher_or_combinator.new(self, *matchers)
  end

  def not
    Matcher_not_combinator.new(self)
  end

  def bindear(un_objeto, diccionario)
  end
end

class Symbol
  include Matcher

  def call(valor)
    true
  end

  def bindear(un_objeto, diccionario)
    diccionario[self] = un_objeto
  end
end

module Bindea
  attr_accessor :matchers

  def initialize(un_matcher, *matchers)
    self.matchers = matchers
    self.matchers << un_matcher
  end

  def bindear(un_objeto, diccionario)
    matchers.each do |matcher|
      matcher.bindear(un_objeto, diccionario)
    end
  end
end

class Matcher_and_combinator
  include Matcher
  include Bindea
  def call(un_objeto)
    self.matchers.all? {|otro_matcher| otro_matcher.call(un_objeto)}
  end
end

class Matcher_or_combinator
  include Matcher
  include Bindea
  def call(un_objeto)
    self.matchers.any? {|otro_matcher| otro_matcher.call(un_objeto)}
  end
end

class Matcher_not_combinator
  include Matcher
  attr_accessor :matcher

  def initialize(matcher)
    self.matcher = matcher
  end

  def call(un_objeto)
    !self.matcher.call(un_objeto)
  end

  def bindear(un_objeto, diccionario)
    matcher.bindear(un_objeto, diccionario)
  end
end

class Matcher_val
  include Matcher

  def initialize(un_valor)
    @valor = un_valor
  end

  def call(un_valor)
    @valor == un_valor
  end
end

class Matcher_type
  include Matcher

  def initialize(un_tipo)
    @tipo = un_tipo
  end

  def call(un_objeto)
    un_objeto.is_a? @tipo
  end
end

class Matcher_duck_typing
  include Matcher

  def initialize(*metodos)
    @metodos = metodos
  end

  def call(un_objeto)
    @metodos.all? {|un_metodo| un_objeto.respond_to?(un_metodo)}
  end
end

class Matcher_list
  include Matcher

  def initialize(una_lista, condicion = true)
    @una_lista = una_lista
    @condicion = condicion
  end

  private def es_matcher(un_objeto)
    un_objeto.class.ancestors.include? Matcher
  end

  private def comparar_listas(lista)
    lista.all? do |valor, otro_valor|
      valor.is_a?(Symbol) || es_matcher(valor) ? valor.call(otro_valor) : val(valor).call(otro_valor)
    end
  end

  def call(otra_lista)
    lista = @una_lista.zip(otra_lista)
    if @condicion
      @una_lista.size == otra_lista.size ? comparar_listas(lista) : false
    else
      comparar_listas(lista)
    end
  end

  def bindear(un_objeto, diccionario)
    i = 0
    @una_lista.each do |elem|
      if elem.methods.include?(:bindear)
        elem.bindear(un_objeto[i], diccionario)
      end
        i += 1
      end
    end
end

class Pattern
  attr_accessor :objeto_matcheable, :lista_with

  def initialize(un_objeto)
    self.objeto_matcheable = un_objeto
    self.lista_with = []
  end

  def with(*matchers, &bloque)
    un_with = With.new(self.objeto_matcheable, matchers, &bloque)
    self.lista_with << un_with
  end

  def otherwise(&bloque)
    un_otherwise = Otherwise.new(&bloque)
    self.lista_with << un_otherwise
  end

  def matchear
    self.lista_with.each do |patron|
      resultado = patron.call
      return resultado if resultado
    end
  end
end

class With
  attr_accessor :objeto_matcheable, :matchers, :bloque, :diccionario

  def initialize(objeto_matcheable, matchers, &bloque)
    self.objeto_matcheable = objeto_matcheable
    self.matchers = matchers
    self.bloque = bloque
    self.diccionario = {}
  end

  def call
    if match
      bindear
      self.instance_eval &self.bloque
    end
  end

  def bindear
    self.matchers.each do |matcher|
      matcher.bindear(self.objeto_matcheable, self.diccionario)
    end
  end

  def match
    self.matchers.all? {|un_matcher| un_matcher.call(self.objeto_matcheable)}
  end

  def method_missing(sym, *args)
    super unless self.diccionario.has_key? sym
    self.diccionario[sym]
  end
end

class Otherwise
  attr_accessor :bloque

  def initialize(&bloque)
    self.bloque = bloque
  end

  def call
    self.instance_eval &self.bloque
  end
end

