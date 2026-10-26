import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { SKU_CRITICALITY_OPTIONS } from './types';
import type { SkuFilter } from './types';

interface Props {
  value: SkuFilter;
  onChange: (next: SkuFilter) => void;
  onReset: () => void;
}

export function SkuFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: SkuFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Sku filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Sku"
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
      <FilterField label="Model Id">
        <Input
          aria-label="Model Id filter"
          placeholder="UUID"
          value={value.modelId ?? ''}
          onChange={(v) => patch({ modelId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Sku Code">
        <Input
          aria-label="Sku Code filter"
          value={value.skuCode ?? ''}
          onChange={(v) => patch({ skuCode: v || undefined })}
        />
      </FilterField>
      <FilterField label="List Price">
        <RangeInputs
          ariaLabel="List Price"
          min={value.listPriceMin}
          max={value.listPriceMax}
          onChange={(min, max) => patch({ listPriceMin: min, listPriceMax: max })}
        />
      </FilterField>
      <FilterField label="Currency">
        <Input
          aria-label="Currency filter"
          value={value.currency ?? ''}
          onChange={(v) => patch({ currency: v || undefined })}
        />
      </FilterField>
      <FilterField label="Lead Time Days">
        <RangeInputs
          ariaLabel="Lead Time Days"
          min={value.leadTimeDaysMin}
          max={value.leadTimeDaysMax}
          onChange={(min, max) => patch({ leadTimeDaysMin: min, leadTimeDaysMax: max })}
        />
      </FilterField>
      <FilterField label="Source System">
        <Input
          aria-label="Source System filter"
          value={value.sourceSystem ?? ''}
          onChange={(v) => patch({ sourceSystem: v || undefined })}
        />
      </FilterField>
      <FilterField label="Region">
        <Input
          aria-label="Region filter"
          value={value.region ?? ''}
          onChange={(v) => patch({ region: v || undefined })}
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
          options={SKU_CRITICALITY_OPTIONS}
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
      <div className="filter-bar__actions">
        <button type="button" className="btn btn--ghost" onClick={onReset}>
          Reset filters
        </button>
      </div>
    </form>
  );
}
