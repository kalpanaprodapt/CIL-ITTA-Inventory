import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { COUNTRY_CRITICALITY_OPTIONS } from './types';
import type { CountryFilter } from './types';

interface Props {
  value: CountryFilter;
  onChange: (next: CountryFilter) => void;
  onReset: () => void;
}

export function CountryFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: CountryFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Country filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Country"
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
      <FilterField label="Iso Code">
        <Input
          aria-label="Iso Code filter"
          value={value.isoCode ?? ''}
          onChange={(v) => patch({ isoCode: v || undefined })}
        />
      </FilterField>
      <FilterField label="Iso Numeric">
        <RangeInputs
          ariaLabel="Iso Numeric"
          min={value.isoNumericMin}
          max={value.isoNumericMax}
          onChange={(min, max) => patch({ isoNumericMin: min, isoNumericMax: max })}
        />
      </FilterField>
      <FilterField label="Dial Code">
        <Input
          aria-label="Dial Code filter"
          value={value.dialCode ?? ''}
          onChange={(v) => patch({ dialCode: v || undefined })}
        />
      </FilterField>
      <FilterField label="Currency">
        <Input
          aria-label="Currency filter"
          value={value.currency ?? ''}
          onChange={(v) => patch({ currency: v || undefined })}
        />
      </FilterField>
      <FilterField label="Regulatory Body">
        <Input
          aria-label="Regulatory Body filter"
          value={value.regulatoryBody ?? ''}
          onChange={(v) => patch({ regulatoryBody: v || undefined })}
        />
      </FilterField>
      <FilterField label="Timezone">
        <Input
          aria-label="Timezone filter"
          value={value.timezone ?? ''}
          onChange={(v) => patch({ timezone: v || undefined })}
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
          options={COUNTRY_CRITICALITY_OPTIONS}
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
