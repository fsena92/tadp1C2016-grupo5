module Variable
  def match(un_atributo)
    self.atributo = un_atributo
  end
end

module Valor
  def val(un_valor)
    self.atributo == un_valor
  end
end

module Tipo
  def type(un_tipo)
    self.atributo.is_a?(un_tipo)
  end
end

module Lista
  def list(una_lista, *matchear_n)
    if matchear_n == [true] || matchear_n == []
      self.atributo.size == una_lista.size && self.atributo == una_lista
    else
      (self.atributo <=> una_lista) != -1 && (self.atributo <=> una_lista) != nil
    end
  end

end

class Matcher
  attr_accessor :atributo
  include Valor
  include Variable
  include Tipo
  include Lista
end
