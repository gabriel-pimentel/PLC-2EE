
math.randomseed(os.time())

local function quem_comeca()
    local caractere

    while true do
        print("Escolha quem começa (pressione p para Pikachu ou pressione r para Raichu): ")
        caractere = io.read()

        if caractere == 'p' or caractere == 'P' then
            return 'p'

        elseif caractere == 'r' or caractere == 'R' then
            return 'r'
        else
            print("Caractere inválido! Digite novamente.")
        end
    end
end

local function escolher_ataque()
    local num = math.random(1, 20)
    if num >= 1 and num <= 10 then
        return "Choque do trovão", 50
    elseif num >= 11 and num <= 15 then
        return "Calda de ferro", 100
    elseif num >= 16 and num <= 18 then
        return "Investida Trovão", 150
    else
        return "Trovão", 200
    end
end



local function ataque_pikachu()
    while true do
        local ataque, dano = escolher_ataque()
        hp_raichu = hp_raichu - dano
        if (hp_raichu < 0) then
           hp_raichu = 0   
        end
        print("Pikachu usou " .. ataque .. " e causou " .. dano .. " de dano.")
        coroutine.yield()
    end
end

local function ataque_raichu()
    while true do
        local ataque, dano = escolher_ataque()
        hp_pikachu = hp_pikachu - dano
        if (hp_pikachu < 0) then
           hp_pikachu = 0   
        end
        print("Raichu usou " .. ataque .. " e causou " .. dano .. " de dano.")
        coroutine.yield()
    end
end

function batalha()
    hp_pikachu = 800
    hp_raichu = 1000
    turno = 1

    local coro_pikachu = coroutine.create(ataque_pikachu)
    local coro_raichu = coroutine.create(ataque_raichu)

    while hp_pikachu > 0 and hp_raichu > 0 do
        if turno % 2 == 1 then
            coroutine.resume(coro_pikachu)
            os.execute("sleep 2")
        else
            coroutine.resume(coro_raichu)
            os.execute("sleep 2")
        end

        -- Exibe os HPs atuais dos Pokémons
        print("Pikachu HP: " .. hp_pikachu .. " | Raichu HP: " .. hp_raichu)
        print()

        turno = turno + 1
    end

    -- Verifica qual Pokémon venceu a batalha
    if hp_pikachu <= 0 then
        print("Raichu venceu a batalha!")
    else
        print("Pikachu venceu a batalha!")
    end
end

batalha()