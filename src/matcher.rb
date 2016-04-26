class Symbol
  def call(v)
    true
  end
end

module Valor
  def val(un_valor)
    proc {|v| v == un_valor}
  end
end

module Tipo
  def type(un_tipo)
    proc {|t| t.is_a?(un_tipo)}
  end
end

module Duck_Typing
  def duck(*metodos)
    proc {|un_objeto| metodos.all? { |metodo| un_objeto.methods.include?(metodo)}}
  end
end

module Lista
  def list(una_lista, *condicion)
    if condicion == [true] || condicion == []
      proc {|otra_lista| una_lista == otra_lista}
    else
      proc {|otra_lista| compare_lists(una_lista, otra_lista)}
    end
  end

  def compare_lists(list_a, list_b)
    list_a[0, list_b.size] == list_b || list_b[0, list_a.size] == list_a
  end
end

class Object
  include Valor
  include Tipo
  include Lista
  include Duck_Typing
end
