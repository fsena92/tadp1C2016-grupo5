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






end