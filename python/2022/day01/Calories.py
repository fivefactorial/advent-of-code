from pathlib import Path

THIS_DIR = Path(__file__).parent
INPUT_FILE = Path(THIS_DIR, "input")

def parse():
    with open('input', 'rt') as f:
        text = f.read()

    elfs = text.split("\n\n")

    calories = []
    for elf in elfs:
        elf_calories = sum(map(int, elf.splitlines()))
        calories.append(elf_calories)

    return sorted(calories)

if __name__ == "__main__":
    data = parse()
    print(f"Part A: {sum(data[-1:])}")
    print(f"Part B: {sum(data[-3:])}")    
