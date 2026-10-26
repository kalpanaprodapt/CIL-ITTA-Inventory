interface Props {
  page: number;
  size: number;
  totalElements: number;
  totalPages: number;
  sizes: number[];
  onPage: (page: number) => void;
  onSize: (size: number) => void;
}

export function Pagination({ page, size, totalElements, totalPages, sizes, onPage, onSize }: Props) {
  const first = totalElements === 0 ? 0 : page * size + 1;
  const last = Math.min(totalElements, (page + 1) * size);
  return (
    <nav className="pagination" aria-label="Pagination">
      <span>
        {first}–{last} of {totalElements}
      </span>
      <label>
        Rows
        <select value={size} onChange={(ev) => onSize(Number(ev.target.value))}>
          {sizes.map((s) => (
            <option key={s} value={s}>
              {s}
            </option>
          ))}
        </select>
      </label>
      <button type="button" disabled={page <= 0} onClick={() => onPage(page - 1)}>
        Previous
      </button>
      <button type="button" disabled={page + 1 >= totalPages} onClick={() => onPage(page + 1)}>
        Next
      </button>
    </nav>
  );
}
