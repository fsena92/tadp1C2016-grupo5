require 'rspec'
require_relative '../src/matcher'

describe 'tests_tp_tadp_matcher' do

  let(:matcher) {
    Matcher.new
  }

  it 'test matcher variable' do
    expect(matcher.match('algo')).to eq('algo')
    expect(matcher.match(1)).to eq(1)
  end

  it 'test no matchea con la variable' do
    expect(matcher.match(1)).not_to eq(2)
  end

  it 'test matcher valor' do
    matcher.atributo = 10
    expect(matcher.val(10)).to be(true)
  end

  it 'test no matchea con el valor' do
    matcher.atributo = 10
    expect(matcher.val(1)).not_to be(true)
  end

  it 'test matchea el tipo' do
    matcher.atributo = 1.0
    expect(matcher.type(Float)).to eq(true)
  end

  it 'test no matchea el tipo' do
    matcher.atributo = 'palabra'
    expect(matcher.type(Float)).to eq(false)
  end

  it 'test lista' do
    matcher.atributo = [1,2,3,4]
    expect(matcher.list([1,2,3,4], true)).to be(true)
    expect(matcher.list([1,2,3,4], false)).to be(true)
    expect(matcher.list([1,2,3], true)).to be(false)
    expect(matcher.list([1,2,3], false)).to be(true)
    expect(matcher.list([2,1,3,4], true)).to be(false)
    expect(matcher.list([2,1,3,4], false)).to be(false)
    expect(matcher.list([1,2,3])).to be(false)
  end

  it 'test lista con matchers' do
    matcher.atributo = [1,2,3,4]
    a = Matcher.new.match(1)
    b = Matcher.new.match(2)
    c = Matcher.new.match(3)
    d = Matcher.new.match(4)
    expect(matcher.list([a, b, c, d])).to eq(true)
  end


  #it 'test lista matchers y otros que no son' do
  #  matcher.atributo = [1,2,3,4]
  #  a = Matcher.new.match(1)
  #  b = Matcher.new.match(2)
  #  expect(matcher.list([a, b.val(2), 3, 4])).to eq(true)
  #end


















end
