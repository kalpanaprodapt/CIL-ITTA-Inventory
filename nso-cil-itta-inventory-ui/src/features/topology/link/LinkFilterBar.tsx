import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { LINK_LINK_TYPE_OPTIONS, LINK_CRITICALITY_OPTIONS } from './types';
import type { LinkFilter } from './types';

interface Props {
  value: LinkFilter;
  onChange: (next: LinkFilter) => void;
  onReset: () => void;
}

export function LinkFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: LinkFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Link filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Link"
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
      <FilterField label="A Interface Id">
        <Input
          aria-label="A Interface Id filter"
          placeholder="UUID"
          value={value.aInterfaceId ?? ''}
          onChange={(v) => patch({ aInterfaceId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Z Interface Id">
        <Input
          aria-label="Z Interface Id filter"
          placeholder="UUID"
          value={value.zInterfaceId ?? ''}
          onChange={(v) => patch({ zInterfaceId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Link Type">
        <MultiSelect
          ariaLabel="Link Type filter"
          options={LINK_LINK_TYPE_OPTIONS}
          value={value.linkType ?? []}
          onChange={(v) => patch({ linkType: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Speed Mbps">
        <RangeInputs
          ariaLabel="Speed Mbps"
          min={value.speedMbpsMin}
          max={value.speedMbpsMax}
          onChange={(min, max) => patch({ speedMbpsMin: min, speedMbpsMax: max })}
        />
      </FilterField>
      <FilterField label="Discovered By">
        <Input
          aria-label="Discovered By filter"
          value={value.discoveredBy ?? ''}
          onChange={(v) => patch({ discoveredBy: v || undefined })}
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
          options={LINK_CRITICALITY_OPTIONS}
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
