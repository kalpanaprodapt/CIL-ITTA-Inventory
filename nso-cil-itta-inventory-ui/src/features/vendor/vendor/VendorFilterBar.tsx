import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { VENDOR_TIER_OPTIONS, VENDOR_LIFECYCLE_STAGE_OPTIONS } from './types';
import type { VendorFilter } from './types';

interface Props {
  value: VendorFilter;
  onChange: (next: VendorFilter) => void;
  onReset: () => void;
}

export function VendorFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: VendorFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Vendor filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Vendor"
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
      <FilterField label="Legal Name">
        <Input
          aria-label="Legal Name filter"
          value={value.legalName ?? ''}
          onChange={(v) => patch({ legalName: v || undefined })}
        />
      </FilterField>
      <FilterField label="Support Email">
        <Input
          aria-label="Support Email filter"
          value={value.supportEmail ?? ''}
          onChange={(v) => patch({ supportEmail: v || undefined })}
        />
      </FilterField>
      <FilterField label="Support Phone">
        <Input
          aria-label="Support Phone filter"
          value={value.supportPhone ?? ''}
          onChange={(v) => patch({ supportPhone: v || undefined })}
        />
      </FilterField>
      <FilterField label="Website">
        <Input
          aria-label="Website filter"
          value={value.website ?? ''}
          onChange={(v) => patch({ website: v || undefined })}
        />
      </FilterField>
      <FilterField label="Tier">
        <MultiSelect
          ariaLabel="Tier filter"
          options={VENDOR_TIER_OPTIONS}
          value={value.tier ?? []}
          onChange={(v) => patch({ tier: v.length ? v : undefined })}
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
      <FilterField label="Lifecycle Stage">
        <MultiSelect
          ariaLabel="Lifecycle Stage filter"
          options={VENDOR_LIFECYCLE_STAGE_OPTIONS}
          value={value.lifecycleStage ?? []}
          onChange={(v) => patch({ lifecycleStage: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Source System">
        <Input
          aria-label="Source System filter"
          value={value.sourceSystem ?? ''}
          onChange={(v) => patch({ sourceSystem: v || undefined })}
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
