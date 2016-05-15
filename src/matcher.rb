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
    pattern_context = PatternMatchingContext.new(un_objeto)
    pattern_context.instance_eval &bloque
    pattern_context.matchear
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

  def initialize(un_matcher, *matchers)
    @matchers = matchers
    @matchers << un_matcher
  end

  def bindear(un_objeto, diccionario)
    @matchers.each {|matcher| matcher.bindear(un_objeto, diccionario)}
  end

end


class Matcher_and_combinator
  include Matcher
  include Bindea

  def call(un_objeto)
    @matchers.all? {|otro_matcher| otro_matcher.call(un_objeto)}
  end

end


class Matcher_or_combinator
  include Matcher
  include Bindea

  def call(un_objeto)
    @matchers.any? {|otro_matcher| otro_matcher.call(un_objeto)}
  end

end


class Matcher_not_combinator
  include Matcher

  def initialize(matcher)
    @matcher = matcher
  end

  def call(un_objeto)
    !@matcher.call(un_objeto)
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
    @matchers = una_lista.map {|elem| !es_matcher(elem) ? val(elem) : elem}
    @condicion = condicion
  end

  private def es_matcher(un_objeto)
    un_objeto.class.ancestors.include? Matcher
  end

  private def comparar_listas(lista)
    lista.all? {|un_matcher, otro_valor| un_matcher.call(otro_valor)}
  end

  def call(otra_lista)
    if otra_lista.is_a?(Array)
      lista = @matchers.zip(otra_lista)
      if @condicion
        @matchers.size == otra_lista.size ? comparar_listas(lista) : false
      else
        comparar_listas(lista)
      end
    else
      false
    end
  end

  def bindear(un_objeto, diccionario)
    if call(un_objeto)          #call se fija si matchea
      @matchers.zip(un_objeto).each {|match_list, elem_list| match_list.bindear(elem_list, diccionario)}
    end
  end

end


class PatternMatchingContext

  def initialize(un_objeto)
    @objeto_matcheable = un_objeto
    @lista_pattern = []
  end

  def with(*matchers, &bloque)
    un_with = With.new(@objeto_matcheable, matchers, &bloque)
    @lista_pattern << un_with
  end

  def otherwise(&bloque)
    un_otherwise = Otherwise.new(&bloque)
    @lista_pattern << un_otherwise
  end

  def matchear
    aux = @lista_pattern.detect {|patron| patron.match}
    aux != nil ? aux.call : (raise MatchError)
  end

end


class With

  def initialize(objeto_matcheable, matchers, &bloque)
    @objeto_matcheable = objeto_matcheable
    @matchers = matchers
    @bloque = bloque
    @diccionario = {}
  end

  def call
    bindear
    self.instance_eval &@bloque
  end

  def bindear
    @matchers.each do |matcher|
      matcher.bindear(@objeto_matcheable, @diccionario)
    end
  end

  def match
    @matchers.all? {|un_matcher| un_matcher.call(@objeto_matcheable)}
  end

  def method_missing(sym, *args)
    super unless @diccionario.has_key? sym
    @diccionario[sym]
  end

end


class Otherwise

  def initialize(&bloque)
    @bloque = bloque
  end

  def call
    self.instance_eval &@bloque
  end

  def match
    true
  end

end


class MatchError < StandardError
end