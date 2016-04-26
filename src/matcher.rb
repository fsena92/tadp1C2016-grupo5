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
  def duck(*mensajes)
    proc do |un_objeto|
      lista_mensajes = un_objeto.class.instance_methods(false) | un_objeto.methods
      mensajes.all? {|mensaje| lista_mensajes.include?(mensaje)}
    end
  end
end

module Lista
  def list(una_lista, *condicion)
    proc do |otra_lista|
      if condicion == [true] || condicion == []
        una_lista.size == otra_lista.size && (otra_lista <=> una_lista) == 0
      else
        (otra_lista <=> una_lista) != -1 && (otra_lista <=> una_lista) != nil
      end
    end
  end
end

class Object
  include Valor
  include Tipo
  include Lista
  include Duck_Typing
end
