import type { ReactNode } from 'react';

export interface Column<T> {
  key: string;
  header: string;
  sortable?: boolean;
  align?: 'left' | 'right';
  hiddenByDefault?: boolean;
  render: (row: T) => ReactNode;
}

interface Props<T> {
  columns: Column<T>[];
  rows: T[];
  rowKey: (row: T) => string;
  loading?: boolean;
  sort?: string;
  onSort?: (sort: string) => void;
  selectable?: boolean;
  selected?: string[];
  onSelectionChange?: (ids: string[]) => void;
}

export function DataTable<T>(props: Props<T>) {
  const { columns, rows, rowKey, loading, sort, onSort, selectable, selected = [], onSelectionChange } = props;
  const [sortField, sortDir] = (sort ?? '').split(',');

  function toggleSort(key: string) {
    if (!onSort) return;
    const next = sortField === key && sortDir === 'asc' ? `${key},desc` : `${key},asc`;
    onSort(next);
  }

  function toggleRow(id: string) {
    onSelectionChange?.(selected.includes(id) ? selected.filter((s) => s !== id) : [...selected, id]);
  }

  const allSelected = rows.length > 0 && rows.every((r) => selected.includes(rowKey(r)));

  return (
    <div className="table-wrap" aria-busy={loading}>
      <table className="data-table">
        <thead>
          <tr>
            {selectable ? (
              <th scope="col">
                <input
                  type="checkbox"
                  aria-label="Select all"
                  checked={allSelected}
                  onChange={() => onSelectionChange?.(allSelected ? [] : rows.map(rowKey))}
                />
              </th>
            ) : null}
            {columns.map((c) => (
              <th
                key={c.key}
                scope="col"
                style={{ textAlign: c.align ?? 'left' }}
                aria-sort={sortField === c.key ? (sortDir === 'desc' ? 'descending' : 'ascending') : 'none'}
              >
                {c.sortable ? (
                  <button type="button" className="th-sort" onClick={() => toggleSort(c.key)}>
                    {c.header}
                  </button>
                ) : (
                  c.header
                )}
              </th>
            ))}
          </tr>
        </thead>
        <tbody>
          {rows.map((row) => {
            const id = rowKey(row);
            return (
              <tr key={id} className={selected.includes(id) ? 'is-selected' : undefined}>
                {selectable ? (
                  <td>
                    <input
                      type="checkbox"
                      aria-label={`Select ${id}`}
                      checked={selected.includes(id)}
                      onChange={() => toggleRow(id)}
                    />
                  </td>
                ) : null}
                {columns.map((c) => (
                  <td key={c.key} style={{ textAlign: c.align ?? 'left' }}>
                    {c.render(row)}
                  </td>
                ))}
              </tr>
            );
          })}
        </tbody>
      </table>
    </div>
  );
}
