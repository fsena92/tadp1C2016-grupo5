require 'rspec'
require_relative '../src/matcher'


describe 'tests_tp_tadp_matcher' do

  module Defensor
  end

  module Atacante
  end

  class Muralla
    include Defensor
  end

  class Guerrero
    include Defensor
    include Atacante
  end

  class Misil
    include Atacante
  end

  class Dragon
    def fly
      'do some flying'
    end
  end

  pattern = Pattern.new

  it 'test matcher variable' do
    expect(:a_variable_name.call('nombre')).to be(true)
  end

  it 'test matcher valor' do
    expect(val(10).call(10)).to eq(true)
  end

  it 'test no matchea con el valor' do
    expect(val(1).call(10)).not_to be(true)
  end

  it 'test matchea el tipo' do
    expect(type(Float).call(1.0)).to eq(true)
  end

  it 'test no matchea el tipo' do
    expect(type(Float).call(10)).to eq(false)
  end

  it 'test duck typing' do
    psyduck = Object.new
    def psyduck.cuack
      'psy..duck?'
    end
    def psyduck.fly
      '(headache)'
    end

    a_dragon = Dragon.new

    expect(duck(:cuack, :fly).call(psyduck)).to eq(true)
    expect(duck(:cuack, :fly).call(a_dragon)).to eq(false)
    expect(duck(:fly).call(a_dragon)).to eq(true)
    expect(duck(:to_s).call(Object.new)).to eq(true)
    expect(duck(:+).call(10)).to be(true)
  end

  it 'test combinator and' do
    expect(type(Defensor).and(type(Atacante)).call(Muralla.new)).to eq(false)
    expect(type(Defensor).and(type(Atacante)).call(Guerrero.new)).to eq(true)
    expect(duck(:+).and(type(Fixnum), val(5)).call(5)).to eq(true)
  end

  it 'test combinator or' do
    expect(type(Defensor).or(type(Atacante)).call(Muralla.new)).to eq(true)
    expect(type(Defensor).or(type(Atacante)).call('un delfin')).to eq(false)
    expect(duck(:sarlompa).or(type(String), val('sasd')).call(5)).to eq(false)
  end

  it 'test combinator not' do
    expect(type(Defensor).not.call(Muralla.new)).to eq(false)
    expect(type(Defensor).not.call(Misil.new)).to eq(true)
  end

  it 'test combinator symbol' do
    expect(:y.and(type(Fixnum)).call(5)).to eq(true)
  end

  it 'test combinator varios' do
    expect(:y.and(:a.or(duck(:+).not, val(5), type(Fixnum).not)).call(5)).to eq(true)
  end

  it 'el test list con false' do
    un_array = [1,2,3,4]
    expect(list([1,2,3,4], false).call(un_array)).to be(true)
    expect(list([1,2,3], false).call(un_array)).to be(true)
    expect(list([2,1,3,4], false).call(un_array)).to eq(false)
    expect(list([1,2,3], false).call([1,2,2,4])).to be(false)
  end

  it 'el test list con true' do
    un_array = [1,2,3,4]
    expect(list([1,2,3,4], true).call(un_array)).to be(true)
    expect(list([1,2,3], true).call(un_array)).to be(false)
    expect(list([2,1,3,4], true).call(un_array)).to be(false)
    expect(list([1,2,3]).call(un_array)).to be(false)
  end

  it 'lista con simbols matchean' do
    expect(list([:a, :b, :c, :d]).call([1,2,3,4])).to be(true)
    expect(list([:a, :b]).call([1,2,3])).to be(false)
    expect(list([:a, :b, :c, :d], false).call([1,2,3,4,5])).to eq(true)
  end

  it 'matcher de lista con diferentes matchers ' do
    expect(list([:b, val(2), duck(:+)]).call([1,2,3])).to eq(true)
  end

  it 'test matchea un string con matchers diferentes' do
    pattern.objeto_matcheable = 'hola'
    expect(pattern.match([type(String), :a_string, val('hola')])).to be(true)
  end

  it 'test matchea un valor con matchers diferentes' do
    pattern.objeto_matcheable = 10
    expect(pattern.match([type(Fixnum), :un_valor, val(10), duck(:+)])).to be(true)
  end

  it 'test matchea una lista de matchers simples y compuestos por lists' do
    pattern.objeto_matcheable = [1,2]
    expect(pattern.match([list([1, 2])])).to be(true)
    expect(pattern.match([list([:a, :b])])).to be(true)
    expect(pattern.match([list([:a, :b]), list([1, 2]), list([val(1), val(2)])])).to be(true)
  end

  it 'test matchea una lista con ' do
    pattern.objeto_matcheable = [1,2,Object.new]
    expect((duck(:+).and(type(Fixnum), :x)).call(1)).to eq(true)
    expect((:y.or(val(4))).call(2)).to eq(true)
    expect((duck(:+).not).call(Object.new)).to eq(true)
    expect(pattern.match([list([duck(:+).and(type(Fixnum), :x), :y.or(val(4)), duck(:+).not])])).to be(true)
  end

  it 'test Patterns con type y variables con cadena' do
    pattern.objeto_matcheable = 'hola'
    expect(pattern.with(type(String), :a_string) { a_string.length }).to eq(4)
    pattern.objeto_matcheable = 10
    expect(pattern.with(type(Integer), :size) { size }).to eq(10)
  end


  it 'test Patterns con listas y with con [1,2]' do
    pattern.objeto_matcheable = [1,2]
    expect(pattern.with(list([:a, :b])) { a + b } ).to eq(3)
  end

  it 'test Patterns con listas de matchers con [1,2,Objeto]' do
    pattern.objeto_matcheable = [1,2,Object.new]
    expect(pattern.with(list([duck(:+).and(type(Fixnum), :x), :y.or(val(4)), duck(:+).not])) { x + y }).to eq(3)
  end

  it 'test Patterns con lista [1,2,3]' do
    pattern.objeto_matcheable = [1,2,3]
    expect(pattern.with(list([:a, val(2), duck(:+)])) { a + 2 }).to eq(3)
  end

  it 'test Patterns con envio de mensaje a un objeto ' do
    x = Object.new
    x.send(:define_singleton_method, :hola) { 'hola' }
    pattern.objeto_matcheable = x
    expect(pattern.with(duck(:hola)) { 'chau!' }).to eq('chau!')
  end

  it 'test Pattern matcher de variable con lista' do
    pattern.objeto_matcheable = [2,4]
    expect(pattern.with(:y.and(list([val(2),:b]))) {y.size + b}).to eq(6)
  end



end