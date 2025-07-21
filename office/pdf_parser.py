import fitz  # PyMuPDF
import os

def extract_text_from_pdf(filename: str) -> str:
    base_path = os.path.dirname(__file__)
    file_path = os.path.join(base_path, "pdf", filename)

    doc = fitz.open(file_path)
    text = ""
    for page in doc:
        text += page.get_text()
    return text