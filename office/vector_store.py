from sentence_transformers import SentenceTransformer
import faiss

class VectorStore:
    def __init__(self):
        self.model = SentenceTransformer("all-MiniLM-L6-v2")
        self.text_chunks = []
        self.index = faiss.IndexFlatL2(384)

    def split_text(self, text: str, chunk_size=300, overlap=50):
        words = text.split()
        chunks = []
        for i in range(0, len(words), chunk_size - overlap):
            chunk = " ".join(words[i:i + chunk_size])
            if chunk.strip():  # 빈 청크 방지
                chunks.append(chunk)
        self.text_chunks = chunks

    def create_index(self):
        if not self.text_chunks:
            print("⚠️ 청크가 비어 있어 벡터 생성 불가")
            return
        embeddings = self.model.encode(self.text_chunks)
        self.index.add(embeddings)

    def search(self, query: str, top_k=3):
        if not self.text_chunks or self.index.ntotal == 0:
            print("⚠️ 검색할 인덱스가 없습니다.")
            return ["문서가 비어 있습니다."]
        query_vec = self.model.encode([query])
        _, indices = self.index.search(query_vec, top_k)
        return [self.text_chunks[i] for i in indices[0] if i < len(self.text_chunks)]
