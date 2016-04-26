require 'rspec'
require_relative '../src/matcher'

describe 'tests_tp_tadp_matcher' do

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

  it 'el test list' do
    un_array = [1,2,3,4]
    expect(list([1,2,3,4], true).call(un_array)).to be(true)
    expect(list([1,2,3,4], false).call(un_array)).to be(true)
    expect(list([1,2,3], true).call(un_array)).to be(false)
    expect(list([1,2,3], false).call(un_array)).to be(true)
    expect(list([2,1,3,4], true).call(un_array)).to be(false)
    expect(list([2,1,3,4], false).call(un_array)).to eq(false)
    expect(list([1,2,3]).call(un_array)).to be(false)
  end

  #it 'test lista con matchers' do
  #un_array = [1,2,3,4]
  #  expect(list([:a, :b, :c, :d]).call(un_array)).to be(true)
  #end

  #it 'test lista matchers y otros que no son' do
  #  matcher.atributo = [1,2,3,4]
  #  a = Matcher.new.match(1)
  #  b = Matcher.new.match(2)
  #  expect(matcher.list([a, b.val(2), 3, 4])).to eq(true)
  #end

  it 'test duck typing' do
    psyduck = Object.new
    def psyduck.cuack
      'psy..duck?'
    end
    def psyduck.fly
      '(headache)'
    end

    class Dragon
      def fly
        'do some flying'
      end
    end
    a_dragon = Dragon.new

    duck(:cuack, :fly).call(psyduck)
    duck(:cuack, :fly).call(a_dragon)
    duck(:fly).call(a_dragon)
    duck(:to_s).call(Object.new)
  end





end
