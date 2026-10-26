import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';

import type { InvoiceFilter } from './types';

interface Props {
  value: InvoiceFilter;
  onChange: (next: InvoiceFilter) => void;
  onReset: () => void;
}

export function InvoiceFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: InvoiceFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Invoice filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Invoice"
          placeholder="code, name or description"
          value={value.q ?? ''}
          onChange={(v) => patch({ q: v || undefined })}
        />
      </FilterField>
      <FilterField label="Status">
        <MultiSelect
          ariaLabel="Status filter"
          options={LIFECYCLE_OPTIONS}
          value={value.status ?? []}
          onChange={(v) => patch({ status: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Code">
        <Input
          aria-label="Code filter"
          value={value.code ?? ''}
          onChange={(v) => patch({ code: v || undefined })}
        />
      </FilterField>
      <FilterField label="Name">
        <Input
          aria-label="Name filter"
          value={value.name ?? ''}
          onChange={(v) => patch({ name: v || undefined })}
        />
      </FilterField>
      <FilterField label="External Ref">
        <Input
          aria-label="External Ref filter"
          value={value.externalRef ?? ''}
          onChange={(v) => patch({ externalRef: v || undefined })}
        />
      </FilterField>
      <FilterField label="Invoice Number">
        <Input
          aria-label="Invoice Number filter"
          value={value.invoiceNumber ?? ''}
          onChange={(v) => patch({ invoiceNumber: v || undefined })}
        />
      </FilterField>
      <FilterField label="Po Id">
        <Input
          aria-label="Po Id filter"
          placeholder="UUID"
          value={value.poId ?? ''}
          onChange={(v) => patch({ poId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Amount">
        <RangeInputs
          ariaLabel="Amount"
          min={value.amountMin}
          max={value.amountMax}
          onChange={(min, max) => patch({ amountMin: min, amountMax: max })}
        />
      </FilterField>
      <FilterField label="Due Date">
        <DateRange
          ariaLabel="Due Date"
          from={value.dueDateFrom}
          to={value.dueDateTo}
          onChange={(from, to) => patch({ dueDateFrom: from, dueDateTo: to })}
        />
      </FilterField>
      <FilterField label="Paid">
        <TriState
          ariaLabel="Paid filter"
          value={value.paid}
          onChange={(v) => patch({ paid: v })}
        />
      </FilterField>
      <FilterField label="Cost Center">
        <Input
          aria-label="Cost Center filter"
          value={value.costCenter ?? ''}
          onChange={(v) => patch({ costCenter: v || undefined })}
        />
      </FilterField>
      <FilterField label="Effective From">
        <DateRange
          ariaLabel="Effective From"
          from={value.effectiveFromFrom}
          to={value.effectiveFromTo}
          onChange={(from, to) => patch({ effectiveFromFrom: from, effectiveFromTo: to })}
        />
      </FilterField>
      <FilterField label="Effective To">
        <DateRange
          ariaLabel="Effective To"
          from={value.effectiveToFrom}
          to={value.effectiveToTo}
          onChange={(from, to) => patch({ effectiveToFrom: from, effectiveToTo: to })}
        />
      </FilterField>
      <FilterField label="Labels">
        <Input
          aria-label="Labels filter"
          value={value.labels ?? ''}
          onChange={(v) => patch({ labels: v || undefined })}
        />
      </FilterField>
      <div className="filter-bar__actions">
        <button type="button" className="btn btn--ghost" onClick={onReset}>
          Reset filters
        </button>
      </div>
    </form>
  );
}
