class MatcherBase

  def call(un_valor)

  end

  def and(*matchers)
    MatcherAndCombinator.new(self, *matchers)
  end

  def or(*matchers)
    MatcherOrCombinator.new(self, *matchers)
  end

  def not(*matchers)
    MatcherNotCombinator.new(self)
  end

end


class MatcherAndCombinator < MatcherBase

  def initialize(un_matcher, *matchers)
    @un_matcher = un_matcher
    @matchers = matchers
  end

  def call(un_objeto)
    @un_matcher.call(un_objeto) && @matchers.all? {|otro_matcher| otro_matcher.call(un_objeto)}
  end

end


class MatcherOrCombinator < MatcherBase

  def initialize(un_matcher, *matchers)
    @un_matcher = un_matcher
    @matchers = matchers
  end

  def call(un_objeto)
    @un_matcher.call(un_objeto) || @matchers.any? {|otro_matcher| otro_matcher.call(un_objeto)}
  end

end


class MatcherNotCombinator < MatcherBase

  def initialize(un_matcher)
    @un_matcher = un_matcher
  end

  def call(un_objeto)
    !@un_matcher.call(un_objeto)
  end

end


class MatcherVal < MatcherBase

  def initialize(un_valor)
    @valor = un_valor
  end

  def call(un_valor)
    @valor == un_valor
  end

end


class MatcherType < MatcherBase

  def initialize(un_tipo)
    @tipo = un_tipo
  end

  def call(un_objeto)
    un_objeto.is_a?(@tipo)
  end

end


class MatcherDuckTyping < MatcherBase

  def initialize(*metodos)
    @metodos = metodos
  end

  def call(un_objeto)
    @metodos.all? {|un_metodo| un_objeto.methods.include?(un_metodo)}
  end

end


class MatcherList < MatcherBase

  def initialize(una_lista, *condicion)
    @una_lista = una_lista
    @una_condicion = condicion
  end

  private def esMatcher(un_objeto)
    un_objeto.class.ancestors.include?(MatcherBase)
  end

  private def comparar_listas(hash)
    hash.all? do |valor, otro_valor|
      valor.class == Symbol || esMatcher(valor) ? valor.call(otro_valor) : val(valor).call(otro_valor)
    end
  end

  def call(otra_lista)
    if @una_condicion == [true] || @una_condicion == []
      hash = Hash[@una_lista.zip(otra_lista)]
      @una_lista.size == otra_lista.size ?  comparar_listas(hash) : false
    else
      comparar_listas(Hash[@una_lista.zip(otra_lista)])
    end
  end

end


class Symbol

  def call(valor)
    true
  end

  def and(*matchers)
    MatcherAndCombinator.new(self, *matchers)
  end

  def or(*matchers)
    MatcherOrCombinator.new(self, *matchers)
  end

  def not(*matchers)
    MatcherNotCombinator.new(self)
  end

end


class Object

  def val(un_valor)
    MatcherVal.new(un_valor)
  end

  def type(un_tipo)
    MatcherType.new(un_tipo)
  end

  def duck(*metodos)
    MatcherDuckTyping.new(*metodos)
  end

  def list(una_lista, *condicion)
    MatcherList.new(una_lista, *condicion)
  end

end


class MatcherTest

  attr_accessor :diccionario, :objeto_matcheable

  def initialize
    self.diccionario = {}
  end

  def with(*matchers, &bloque)
    if match(*matchers)
      bindear(*matchers)
      self.instance_eval &bloque
    end
  end

  def other_wise(&bloque)
    self.instance_eval &bloque
  end

  def bindear(*objeto)

  end

  def method_missing(sym, *args)
    self.diccionario[sym]
  end

  def match(matchers)
    matchers.all? {|un_matcher| un_matcher.call(objeto_matcheable)}
  end

end


