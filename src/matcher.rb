class Object
  attr_accessor :un_objeto

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

  def matches?(un_objeto,&bloque)
    self.un_objeto = un_objeto
    self.instance_eval(&bloque)
  end

  def method_missing(sym, *arg,&bloque)

    p = Pattern.new
    p.objeto_matcheable = un_objeto

    if sym.to_s == 'with'
      p.with(*arg,&bloque)

    elsif sym.to_s == 'otherwise'
      p.other_wise(&bloque)
    end

 end

end

module Combinator

  def and(*matchers)
    Matcher_and_combinator.new(self, *matchers)
  end

  def or(*matchers)
    Matcher_or_combinator.new(self, *matchers)
  end

  def not
    Matcher_not_combinator.new(self)
  end
end

class Matcher
  include Combinator
end

class Symbol
  include Combinator

  def call(valor)
    true
  end

  def bindear(un_objeto, diccionario)
    diccionario[self] = un_objeto
  end
end

class Matcher_and_combinator < Matcher
  attr_accessor :un_matcher, :matchers

  def initialize(un_matcher, *matchers)
    self.un_matcher = un_matcher
    self.matchers = matchers
  end

  def call(un_objeto)
    self.un_matcher.call(un_objeto) && self.matchers.all? {|otro_matcher| otro_matcher.call(un_objeto)}
  end

  def bindear(un_objeto, diccionario)
    un_matcher.bindear(un_objeto, diccionario)
    matchers.each do |matcher|
      matcher.bindear(un_objeto, diccionario)
    end
  end

end

class Matcher_or_combinator < Matcher
  attr_accessor :un_matcher, :matchers

  def initialize(un_matcher, *matchers)
    self.un_matcher = un_matcher
    self.matchers = matchers
  end

  def call(un_objeto)
    self.un_matcher.call(un_objeto) || self.matchers.any? {|otro_matcher| otro_matcher.call(un_objeto)}
  end

  def bindear(un_objeto, diccionario)
    un_matcher.bindear(un_objeto, diccionario)
    matchers.each do |matcher|
      matcher.bindear(un_objeto, diccionario)
    end
  end

end

class Matcher_not_combinator < Matcher
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

class Matcher_val < Matcher

  def initialize(un_valor)
    @valor = un_valor
  end

  def call(un_valor)
    @valor == un_valor
  end

  def bindear(un_objeto, diccionario)
  end
end

class Matcher_type < Matcher
  attr_accessor :tipo

  def initialize(un_tipo)
    self.tipo = un_tipo
  end

  def call(un_objeto)
    un_objeto.is_a? self.tipo
  end

  def bindear(un_objeto, diccionario)
  end
end

class Matcher_duck_typing < Matcher
  attr_accessor :metodos

  def initialize(*metodos)
    self.metodos = metodos
  end

  def call(un_objeto)
    self.metodos.all? {|un_metodo| un_objeto.methods.include?(un_metodo)}
  end

  def bindear(un_objeto, diccionario)
  end
end


class Matcher_list < Matcher

  def initialize(una_lista, *condicion)
    @una_lista = una_lista
    @condicion = condicion
  end

  private def es_matcher(un_objeto)
    un_objeto.class.ancestors.include? Matcher
  end

  private def comparar_listas(hash)
    hash.all? do |valor, otro_valor|
      valor.is_a?(Symbol) || es_matcher(valor) ? valor.call(otro_valor) : val(valor).call(otro_valor)
    end
  end

  def call(otra_lista)
    if @condicion == [true] || @condicion == []
      hash = Hash[@una_lista.zip(otra_lista)]
      @una_lista.size == otra_lista.size ?  comparar_listas(hash) : false
    else
      comparar_listas(Hash[@una_lista.zip(otra_lista)])
    end
  end


  def bindear(un_objeto, diccionario)
    i = 0
    @una_lista.each do |elem|
      if elem.methods.include?(:bindear)
        elem.bindear(un_objeto[i], diccionario)
      end
        i+= 1
      end
    end

end

class Pattern
  attr_accessor :diccionario, :objeto_matcheable

  def initialize
    self.diccionario = {}
  end

  def with(*matchers, &bloque)
    if match(matchers)
      bindear(matchers)
      self.instance_eval &bloque
      exit
    end
  end

  def other_wise(&bloque)
    self.instance_eval &bloque
  end

  def bindear(matchers)
    matchers.each do |matcher|
      matcher.bindear(objeto_matcheable,diccionario)
    end
  end

  def method_missing(sym, *args)
    self.diccionario[sym]
  end

  def match(matchers)
    matchers.all? {|un_matcher| un_matcher.call(self.objeto_matcheable)}
  end
end






