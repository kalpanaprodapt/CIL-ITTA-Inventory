import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { PURCHASE_ORDER_CRITICALITY_OPTIONS } from './types';
import type { PurchaseOrderFilter } from './types';

interface Props {
  value: PurchaseOrderFilter;
  onChange: (next: PurchaseOrderFilter) => void;
  onReset: () => void;
}

export function PurchaseOrderFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: PurchaseOrderFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Purchase Order filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Purchase Order"
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
      <FilterField label="Po Number">
        <Input
          aria-label="Po Number filter"
          value={value.poNumber ?? ''}
          onChange={(v) => patch({ poNumber: v || undefined })}
        />
      </FilterField>
      <FilterField label="Vendor Id">
        <Input
          aria-label="Vendor Id filter"
          placeholder="UUID"
          value={value.vendorId ?? ''}
          onChange={(v) => patch({ vendorId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Ordered On">
        <DateRange
          ariaLabel="Ordered On"
          from={value.orderedOnFrom}
          to={value.orderedOnTo}
          onChange={(from, to) => patch({ orderedOnFrom: from, orderedOnTo: to })}
        />
      </FilterField>
      <FilterField label="Total Amount">
        <RangeInputs
          ariaLabel="Total Amount"
          min={value.totalAmountMin}
          max={value.totalAmountMax}
          onChange={(min, max) => patch({ totalAmountMin: min, totalAmountMax: max })}
        />
      </FilterField>
      <FilterField label="Currency">
        <Input
          aria-label="Currency filter"
          value={value.currency ?? ''}
          onChange={(v) => patch({ currency: v || undefined })}
        />
      </FilterField>
      <FilterField label="Owner">
        <Input
          aria-label="Owner filter"
          value={value.owner ?? ''}
          onChange={(v) => patch({ owner: v || undefined })}
        />
      </FilterField>
      <FilterField label="Criticality">
        <MultiSelect
          ariaLabel="Criticality filter"
          options={PURCHASE_ORDER_CRITICALITY_OPTIONS}
          value={value.criticality ?? []}
          onChange={(v) => patch({ criticality: v.length ? v : undefined })}
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
      <div className="filter-bar__actions">
        <button type="button" className="btn btn--ghost" onClick={onReset}>
          Reset filters
        </button>
      </div>
    </form>
  );
}
